import { Routes } from '@angular/router';
import { LoginComponent } from './view-perfiles/login/login.component';
import { RegisterComponent } from './view-perfiles/register/register.component';
import { PaginaPrincipalComponent } from './view-pagina-principal/pagina-principal/pagina-principal.component';
import { ViewCarritoComponent } from './view-carrito/view-carrito.component';
import { RegistrarPagoComponent } from './view-pagos/registrar-pago/registrar-pago.component';
import { ConfirmarPagoComponent } from './view-pagos/confirmar-pago/confirmar-pago.component';
import { authGuard } from './guards/auth.guard';
import { adminGuard } from './guards/admin.guard';
import { guestGuard } from './guards/guest.guard';

export const routes: Routes = [
  { 
    path: '', 
    component: PaginaPrincipalComponent,
    title: 'Satori' 
  },
  { 
    path: 'login', 
    component: LoginComponent,
    title: 'Iniciar Sesión',
    canActivate: [guestGuard]
  },
  { 
    path: 'register', 
    component: RegisterComponent,
    title: 'Registro',
    canActivate: [guestGuard]
  },
  { 
    path: 'registrar-pago', 
    component: RegistrarPagoComponent,
    title: 'Registrar Pago',
    canActivate: [authGuard]
  },
  { 
    path: 'confirmar-pagos',
    component: ConfirmarPagoComponent,
    title: 'Confirmar Pagos (Admin)',
    canActivate: [authGuard, adminGuard]
  },
  { 
    path: 'carrito', 
    component: ViewCarritoComponent,
    title: 'Carrito de Compras',
    canActivate: [authGuard]
  },

  // ✅ RUTA CORRECTA PARA REGISTRO DE PAGOS (antes del wildcard '**')
  { 
    path: 'registro-pagos',
    loadComponent: () => import('./view-pagos/registro-pagos/registro-pagos.component').then(m => m.RegistroPagosComponent),
    title: 'Registro de Pagos',
    canActivate: [authGuard, adminGuard]
  },

  // Redirección para "admin" si necesitas
  { 
    path: 'admin', 
    redirectTo: '',
    pathMatch: 'full'
  },

  // ⛔ Wildcard al final (debe estar al último siempre)
  { 
    path: '**', 
    redirectTo: '' 
  }
];
