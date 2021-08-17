import { Component } from '@angular/core';
import { ApplicationUser, AuthService } from './core/auth/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SnackbarService } from './util/snakbar.service';
import { AppService } from './app.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  user!: ApplicationUser | null;

  constructor(
    private authService: AuthService,
    private snackBar: MatSnackBar,
    private snackbarService: SnackbarService,
    private _appService: AppService
  ) {
    authService.user$.subscribe((u) => {
      this.user = u;
    });
    snackbarService.snackBar = snackBar;
  }
}
