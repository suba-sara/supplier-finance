import { Component } from '@angular/core';
import { ApplicationUser, AuthService } from './core/auth/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  user!: ApplicationUser | null;

  constructor(private authService: AuthService) {
    authService.user$.subscribe((u) => {
      this.user = u;
    });
  }

  title = 'capstone-client';
}
