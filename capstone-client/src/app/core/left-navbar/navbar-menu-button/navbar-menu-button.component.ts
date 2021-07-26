import { Component, OnInit } from '@angular/core';
import { AppService } from '../../../app.service';

@Component({
  selector: 'app-navbar-menu-button',
  templateUrl: './navbar-menu-button.component.html',
  styleUrls: ['./navbar-menu-button.component.scss'],
})
export class NavbarMenuButtonComponent implements OnInit {
  isExpanded = false;

  constructor(private appService: AppService) {
    appService.navbarExpanded$.subscribe((value) => (this.isExpanded = value));
  }

  ngOnInit(): void {}

  toggleNavbar: () => void = () => this.appService.toggleLeftNavbar();
}
