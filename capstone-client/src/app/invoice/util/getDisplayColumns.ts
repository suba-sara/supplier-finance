import { UserType } from '../../core/auth/auth.service';

export function getDisplayColumns(userType?: UserType): string[] {
  switch (userType) {
    case 'CLIENT':
      return [
        'invoiceId',
        'invoiceNumber',
        'supplierId',
        'uploadedDate',
        'invoiceDate',
        'invoiceAge',
        'amount',
        'invoiceStatus',
        'options',
      ];
    case 'SUPPLIER':
      return [
        'invoiceId',
        'invoiceNumber',
        'clientId',
        'uploadedDate',
        'invoiceDate',
        'invoiceAge',
        'amount',
        'invoiceStatus',
        'options',
      ];
    case 'BANKER':
      return [
        'invoiceId',
        'invoiceNumber',
        'clientId',
        'supplierId',
        'uploadedDate',
        'invoiceDate',
        'invoiceAge',
        'amount',
        'invoiceStatus',
        'options',
      ];
    default:
      return [];
  }
}
