export type Invoice = {
  invoiceId: number;
  clientId: string;
  supplierId: string;
  invoiceNumber: number;
  invoiceDate: Date;
  amount: number;
  status: string;
  currencyType: string;
};
