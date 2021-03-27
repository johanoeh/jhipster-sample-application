export interface IAddress {
  id?: number;
  streetAddress?: string;
  postalCode?: number;
  city?: string;
  co?: string;
}

export class Address implements IAddress {
  constructor(public id?: number, public streetAddress?: string, public postalCode?: number, public city?: string, public co?: string) {}
}
