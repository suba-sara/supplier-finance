import { Component, OnInit } from '@angular/core';
import { AppService } from '../../app.service';

@Component({
  selector: 'app-left-navbar',
  templateUrl: './left-navbar.component.html',
  styleUrls: ['./left-navbar.component.scss'],
})
export class LeftNavbarComponent implements OnInit {
  isExpanded = false;

  constructor(private appService: AppService) {
    appService.navbarExpanded$.subscribe((value) => (this.isExpanded = value));
  }

  ngOnInit(): void {}
}
