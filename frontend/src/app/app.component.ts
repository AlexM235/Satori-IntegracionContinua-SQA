import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http'; // Añade esto si usas HTTP

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule,RouterOutlet,HttpClientModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Frontend';
}
