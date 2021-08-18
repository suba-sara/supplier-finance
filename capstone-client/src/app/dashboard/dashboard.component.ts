import { Component, OnInit } from '@angular/core';
import { AppService } from '../app.service';
import { AuthService, ProfileResponse } from '../core/auth/auth.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit {
  profile?: ProfileResponse;

  constructor(
    private appService: AppService,
    private authService: AuthService
  ) {
    authService.getProfile().subscribe((profile) => (this.profile = profile));
  }

  ngOnInit(): void {
    this.appService.setPageTitle('Dashboard');
  }
}
