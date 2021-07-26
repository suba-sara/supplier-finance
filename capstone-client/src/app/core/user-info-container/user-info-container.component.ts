import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-user-info-container',
  templateUrl: './user-info-container.component.html',
  styleUrls: ['./user-info-container.component.scss'],
})
export class UserInfoContainerComponent implements OnInit {
  constructor(public authService: AuthService) {}

  ngOnInit(): void {}

  async signOut(): Promise<void> {
    this.authService.signOut();
    location.reload();
  }
}
