import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-top-navbar',
  templateUrl: './top-navbar.component.html',
  styleUrls: ['./top-navbar.component.scss'],
})
export class TopNavbarComponent implements OnInit {
  isMobileMenuOpen = false;

  constructor() {}

  ngOnInit(): void {}

  toggleMenu = (hide?: boolean): void => {
    if (hide) {
      this.isMobileMenuOpen = false;
    } else {
      this.isMobileMenuOpen = !this.isMobileMenuOpen;
    }
  };
}
