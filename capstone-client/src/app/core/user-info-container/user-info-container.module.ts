import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { RouterModule } from '@angular/router';
import { MatTooltipModule } from '@angular/material/tooltip';
import { AuthModule } from '../auth/auth.module';
import { UserInfoContainerComponent } from './user-info-container.component';

@NgModule({
  declarations: [UserInfoContainerComponent],
  imports: [
    CommonModule,
    AuthModule,
    RouterModule,
    MatIconModule,
    MatButtonModule,
    MatTooltipModule,
  ],
  exports: [UserInfoContainerComponent],
})
export class UserInfoContainerModule {}
