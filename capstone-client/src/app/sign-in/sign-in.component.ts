import {Component, Input, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';

const {API_PATH} = environment;

export type SignInData = {
  username: string;
  password: string;
};

type SignInResponseType = {
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

  constructor(private http: HttpClient) {
    this.signInData = {
      username: '',
      password: ''
    };
  }

  ngOnInit(): void {
  }

  handleSignInClick(): void {
    this.http.post<SignInResponseType>(`${API_PATH}/sign-in`, this.signInData)
      .subscribe((response) => {
        // store data on local storage
        localStorage.setItem('token', response.jwt);
        localStorage.setItem('userType', response.userType);

        // give success feedback and navigate user
      }, (error) => {
        // give error feedback if response returned error
        console.log(error);
      });
  }
}
