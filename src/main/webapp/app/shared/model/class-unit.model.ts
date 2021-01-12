import { Moment } from 'moment';

export interface IClassUnit {
  id?: number;
  day?: string;
  startTime?: string;
  endTime?: string;
  classGroupId?: number;
  roomId?: number;
}

export const defaultValue: Readonly<IClassUnit> = {};
