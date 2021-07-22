import {Pipe, PipeTransform} from '@angular/core';
import * as dayjs from 'dayjs';

@Pipe({
  name: 'InvoiceAge',
})
export class InvoiceAgePipe implements PipeTransform {
  transform(date: any, ...args: any[]): any {
    const formatOutput = (value: number, unit: string) =>
      value === 1 ? `${value} ${unit}` : `${value} ${unit}s`;

    const today = dayjs();

    const diffYears = dayjs(date).diff(today, 'years');
    if (diffYears) {
      return formatOutput(diffYears, 'year');
    }

    const diffMonths = dayjs(date).diff(today, 'months');
    if (diffMonths) {
      return formatOutput(diffMonths, 'month');
    }

    const diffDays = dayjs(date).diff(today, 'days');
    return formatOutput(diffDays, 'day');
  }
}
