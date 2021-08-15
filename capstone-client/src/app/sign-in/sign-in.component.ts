import { Component, OnInit } from '@angular/core';
import { AuthService, SignInData } from '../core/auth/auth.service';
import { Router } from '@angular/router';

export type SignInResponseType = {
  jwt: string;
  userType: string;
};

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss'],
})
export class SignInComponent implements OnInit {
  // declare variables
  signInData: SignInData;
  isPasswordVisible: boolean;
  errorMessage?: string;

  /**
   * Constructor Method
   */
  constructor(private authService: AuthService, private router: Router) {
    // initialize variable
    this.signInData = {
      userId: '',
      password: '',
    };
    this.isPasswordVisible = true;
  }

  ngOnInit(): void {}

  /**
   * Method to handle Sign-in
   */
  handleSignInClick(): void {
    // clear the error messages
    this.errorMessage = undefined;

    // sent user input sign-in data to auth service
    this.authService.signIn(this.signInData).subscribe(
      () => {
        this.router.navigateByUrl('/dashboard');
      },
      (error) => {
        // if error occurs, then set error to errorMessage variable
        this.errorMessage = error;
      }
    );
  }
}
