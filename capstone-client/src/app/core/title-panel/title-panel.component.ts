import { Component, OnInit } from '@angular/core';
import { ApplicationUser } from '../auth/auth.service';
import { AppService } from '../../app.service';

@Component({
  selector: 'app-title-panel',
  templateUrl: './title-panel.component.html',
  styleUrls: ['./title-panel.component.scss'],
})
export class TitlePanelComponent implements OnInit {
  user!: ApplicationUser | null;

  constructor(public appService: AppService) {}

  ngOnInit(): void {}
}
