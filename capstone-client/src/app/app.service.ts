import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Title } from '@angular/platform-browser';

const LEFT_NAVBAR_EXPANDED_STATE_KEY = 'IS_LEFT_NAVBAR_EXPANDED';

@Injectable({
  providedIn: 'root',
})
export class AppService {
  baseTitle = `SHS Bank's Supplier Finance`;
  navbarExpanded$ = new BehaviorSubject(false);
  pageTitle$ = new BehaviorSubject<string | undefined>(undefined);

  constructor(private title: Title) {
    this.pageTitle$.subscribe((pageTitle) => {
      this.title.setTitle(
        pageTitle ? `${pageTitle} - ${this.baseTitle} ` : this.baseTitle
      );
    });

    // get previous leftnavbar state from localstorage
    this.navbarExpanded$.next(
      !!localStorage.getItem(LEFT_NAVBAR_EXPANDED_STATE_KEY)
    );
  }

  toggleLeftNavbar = () => {
    if (!this.navbarExpanded$.value) {
      localStorage.setItem(LEFT_NAVBAR_EXPANDED_STATE_KEY, 'true');
    } else {
      localStorage.removeItem(LEFT_NAVBAR_EXPANDED_STATE_KEY);
    }
    this.navbarExpanded$.next(!this.navbarExpanded$.value);
  };

  setPageTitle = (pageTitle?: string) => {
    this.pageTitle$.next(pageTitle);
  };
}
