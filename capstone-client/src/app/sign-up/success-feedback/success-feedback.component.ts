import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-success-feedback',
  templateUrl: './success-feedback.component.html',
  styleUrls: ['./success-feedback.component.scss'],
})
export class SuccessFeedbackComponent implements OnInit {
  secondsLeft = 3;

  constructor(private router: Router) {}

  ngOnInit(): void {
    const interval = setInterval(() => {
      this.secondsLeft--;
      if (this.secondsLeft === 0) {
        clearInterval(interval);
        this.router.navigate(['sign-in']);
      }
    }, 1000);
  }
}
