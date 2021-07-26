import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Title } from '@angular/platform-browser';

@Injectable({
  providedIn: 'root',
})
export class AppService {
  baseTitle = 'Supplier Finance';
  navbarExpanded$ = new BehaviorSubject(false);
  pageTitle$ = new BehaviorSubject<string | undefined>(undefined);

  constructor(private title: Title) {
    this.pageTitle$.subscribe((pageTitle) => {
      this.title.setTitle(
        pageTitle ? `${this.baseTitle} | ${pageTitle}` : this.baseTitle
      );
    });
  }

  toggleLeftNavbar = () =>
    this.navbarExpanded$.next(!this.navbarExpanded$.value);

  setPageTitle = (pageTitle: string) => {
    this.pageTitle$.next(pageTitle);
  };
}
