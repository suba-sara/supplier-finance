import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-sign-up-home',
  templateUrl: './sign-up-home.component.html',
  styleUrls: ['./sign-up-home.component.scss']
})
export class SignUpHomeComponent implements OnInit {

  accountType?: string;

  constructor() {
  }

  ngOnInit(): void {
  }
}
