import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Usuario } from '../models/usuario.model';
import {
  BehaviorSubject,
  Observable,
  throwError,
  of
} from 'rxjs';
import {
  catchError,
  tap,
  switchMap
} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly apiUrl = '/api/auth';
  private readonly storageKey = 'currentSession';
  private currentUserSubject = new BehaviorSubject<Usuario | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(
    private http: HttpClient,
    private router: Router
  ) {
    this.loadInitialState();
  }

  private loadInitialState(): void {
    const savedSession = localStorage.getItem(this.storageKey);
    if (savedSession) {
      try {
        const user: Usuario = JSON.parse(savedSession);
        if (user?.usuario && user?.rol) {
          this.verifySession(user).subscribe({
            next: (isValid) => {
              if (isValid) {
                this.currentUserSubject.next(user);
              } else {
                this.clearSession();
              }
            },
            error: () => this.clearSession()
          });
        } else {
          this.clearSession();
        }
      } catch {
        this.clearSession();
      }
    }
  }

  private verifySession(user?: Usuario): Observable<boolean> {
    if (!user || !user.usuario) {
      return of(false);
    }

    return this.http.get<Usuario>(`${this.apiUrl}/current?username=${user.usuario}`).pipe(
      switchMap(currentUser => of(currentUser.usuario === user.usuario)),
      catchError(() => of(false))
    );
  }

  login(username: string, password: string): Observable<Usuario> {
    if (!username || !password) {
      return throwError(() => new Error('Usuario y contraseña son requeridos'));
    }

    return this.http.post<Usuario>(`${this.apiUrl}/login`, { username, password }).pipe(
      tap(user => {
        if (!user || !user.usuario) {
          throw new Error('Respuesta inválida del servidor');
        }
        this.currentUserSubject.next(user);
        this.saveSession(user);
      }),
      catchError(error => {
        console.error('Login error:', error);
        let errorMessage = 'Error durante el login';

        if (error.error instanceof ErrorEvent) {
          errorMessage = `Error: ${error.error.message}`;
        } else {
          if (error.status === 401) {
            errorMessage = 'Credenciales inválidas';
          } else if (error.status === 0) {
            errorMessage = 'No se puede conectar al servidor';
          } else if (error.error?.message) {
            errorMessage = error.error.message;
          }
        }

        return throwError(() => new Error(errorMessage));
      })
    );
  }

  register(usuario: string, correo: string, contrasena: string): Observable<Usuario> {
    if (!usuario || !correo || !contrasena) {
      return throwError(() => new Error('Todos los campos son requeridos'));
    }

    return this.http.post<Usuario>(`${this.apiUrl}/register`, {
      usuario,
      correo,
      contrasena,
      rol: 'CLIENTE'
    }).pipe(
      tap(user => {
        if (!user || !user.usuario) {
          throw new Error('Respuesta inválida del servidor');
        }
        this.currentUserSubject.next(user);
        this.saveSession(user);
      }),
      catchError(error => {
        console.error('Register error:', error);
        let errorMessage = 'Error durante el registro';

        if (error.status === 400) {
          errorMessage = error.error?.message || 'Datos inválidos';
        } else if (error.status === 409) {
          errorMessage = 'El usuario ya existe';
        }

        return throwError(() => new Error(errorMessage));
      })
    );
  }

  logout(): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/logout`, {}).pipe(
      tap(() => {
        this.clearSession();
        this.router.navigate(['/login']);
      }),
      catchError(error => {
        this.clearSession();
        this.router.navigate(['/login']);
        return throwError(() => error);
      })
    );
  }

  private saveSession(user: Usuario): void {
    const safeUser = { ...user };
    delete safeUser.contrasena;
    localStorage.setItem(this.storageKey, JSON.stringify(safeUser));
  }

  private clearSession(): void {
    localStorage.removeItem(this.storageKey);
    this.currentUserSubject.next(null);
  }

  getCurrentUser(): Usuario | null {
    return this.currentUserSubject.value;
  }

  isAuthenticated(): boolean {
    return this.getCurrentUser() !== null;
  }

  isAdmin(): boolean {
    return this.getCurrentUser()?.rol === 'ADMIN';
  }
}
