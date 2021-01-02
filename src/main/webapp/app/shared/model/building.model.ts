import { IRoom } from 'app/shared/model/room.model';

export interface IBuilding {
  id?: number;
  name?: string;
  place?: string;
  postcode?: string;
  street?: string;
  number?: string;
  longitude?: number;
  latitude?: number;
  rooms?: IRoom[];
}

export const defaultValue: Readonly<IBuilding> = {};
