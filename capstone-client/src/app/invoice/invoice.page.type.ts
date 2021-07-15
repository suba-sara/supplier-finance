import { Invoice } from './invoice.types';

type Sort = {
  sorted: boolean;
  unsorted: boolean;
  empty: boolean;
};

type Pageable = {
  sort: Sort;
  offset: 0;
  pageSize: 10;
  pageNumber: 0;
  unpaged: false;
  paged: true;
};

export type InvoicePageType = {
  content: Invoice[];
  pageable: Pageable;
  last: boolean;
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  sort: Sort;
  numberOfElements: number;
  first: boolean;
  empty: boolean;
};
