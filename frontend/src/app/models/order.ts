export interface Order {
  id: number;
  autoId: number;
  description: string;
  acceptanceDate: string;
  finishedDate: string;
  dutyIds: Array<number>;
  productIds: Array<number>;
  status: string;
  totalPrice: number;
}
