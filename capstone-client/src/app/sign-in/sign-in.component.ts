import {Component, Input, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AuthService, SignInData} from '../auth/auth.service';


export type SignInResponseType = {
  jwt: string,
  userType: string,
};

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss']
})

export class SignInComponent implements OnInit {
  @Input() signInData: SignInData;

  constructor(private http: HttpClient, private authService: AuthService) {
    this.signInData = {
      username: '',
      password: ''
    };
  }

  ngOnInit(): void {
  }

  handleSignInClick(): void {
    this.authService.signIn(this.signInData, (res) => {
      if (res.status === 200) {
        // give success feedback to user and navigate
      } else if (res.status === 403) {
        // give error feedback to user
      }
    });
  }
}
