import {Component, OnInit} from '@angular/core';
import {AuthService} from '../auth/auth.service';

@Component({
  selector: 'app-left-navbar',
  templateUrl: './left-navbar.component.html',
  styleUrls: ['./left-navbar.component.scss'],
})
export class LeftNavbarComponent implements OnInit {
  userTypeApiPath?: string;

  constructor(private authService: AuthService) {
    authService.user$.subscribe((res: any) => {
      this.userTypeApiPath = res.userType;
    });
    console.log(this.userTypeApiPath);
  }

  ngOnInit(): void {
  }
}
