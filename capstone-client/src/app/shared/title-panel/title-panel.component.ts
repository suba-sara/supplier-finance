import { Component, OnInit } from '@angular/core';
import { ApplicationUser, AuthService } from '../../core/auth/auth.service';

@Component({
  selector: 'app-title-panel',
  templateUrl: './title-panel.component.html',
  styleUrls: ['./title-panel.component.scss'],
})
export class TitlePanelComponent implements OnInit {
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
