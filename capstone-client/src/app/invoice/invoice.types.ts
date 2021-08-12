type Client = {
  clientId: string;
  name: string;
};

type Supplier = {
  supplierId: string;
  name: string;
};

export type InvoiceStatus = 'UPLOADED' | 'IN_REVIEW' | 'APPROVED' | 'REJECTED';

export type Invoice = {
  invoiceId: number;
  client: Client;
  supplier: Supplier;
  invoiceNumber: number;
  uploadedDate: Date;
  invoiceDate: Date;
  amount: number;
  status: InvoiceStatus;
  currencyType: string;
  fileUrl: string;
};
