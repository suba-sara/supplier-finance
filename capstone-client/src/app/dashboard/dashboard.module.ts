import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardComponent } from './dashboard.component';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTooltipModule } from '@angular/material/tooltip';
import { RouterModule } from '@angular/router';
import { SharedModule } from '../shared/shared.module';
import { LeftNavbarModule } from '../core/left-navbar/left-navbar.module';

@NgModule({
  declarations: [DashboardComponent],
  imports: [
    CommonModule,
    MatIconModule,
    MatButtonModule,
    MatTooltipModule,
    RouterModule,
    SharedModule,
    LeftNavbarModule,
  ],
})
export class DashboardModule {}
