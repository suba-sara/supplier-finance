import { Component, OnInit } from '@angular/core';
import { ApplicationUser, AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-left-navbar',
  templateUrl: './left-navbar.component.html',
  styleUrls: ['./left-navbar.component.scss'],
})
export class LeftNavbarComponent implements OnInit {
  user!: ApplicationUser | null;

  constructor(private authService: AuthService) {
    this.authService.user$.subscribe((u) => {
      this.user = u;
    });
  }

  ngOnInit(): void {}

  signOut(): void {
    this.authService.signOut();
    window.location.href = '/';
  }
}
