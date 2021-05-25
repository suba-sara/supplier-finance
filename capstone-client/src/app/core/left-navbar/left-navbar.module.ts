import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LeftNavbarComponent } from './left-navbar.component';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { RouterModule } from '@angular/router';
import { MatTooltipModule } from '@angular/material/tooltip';
import { ExpandableNavLinkComponent } from './expandable-nav-link/expandable-nav-link.component';
import { MatRippleModule } from '@angular/material/core';
import { NavLinkComponent } from './nav-link/nav-link.component';
import { AuthModule } from '../auth/auth.module';
import { SharedModule } from '../../shared/shared.module';

@NgModule({
  declarations: [
    LeftNavbarComponent,
    ExpandableNavLinkComponent,
    NavLinkComponent,
  ],
  imports: [
    CommonModule,
    AuthModule,
    RouterModule,
    MatIconModule,
    MatButtonModule,
    MatTooltipModule,
    MatRippleModule,
    SharedModule,
  ],
  exports: [LeftNavbarComponent],
})
export class LeftNavbarModule {}
