import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { RouterModule } from '@angular/router';
import { MatTooltipModule } from '@angular/material/tooltip';
import { AuthModule } from '../auth/auth.module';
import { TitlePanelComponent } from './title-panel.component';
import { LeftNavbarModule } from '../left-navbar/left-navbar.module';
import { UserInfoContainerModule } from '../user-info-container/user-info-container.module';

@NgModule({
  declarations: [TitlePanelComponent],
  imports: [
    CommonModule,
    AuthModule,
    RouterModule,
    MatIconModule,
    MatButtonModule,
    MatTooltipModule,
    LeftNavbarModule,
    UserInfoContainerModule,
  ],
  exports: [TitlePanelComponent],
})
export class TitlePanelModule {}
