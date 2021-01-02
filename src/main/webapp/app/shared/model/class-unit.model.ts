import { Moment } from 'moment';

export interface IClassUnit {
  id?: number;
  day?: Moment;
  startTime?: Moment;
  endTime?: Moment;
  classGroupId?: number;
  roomId?: number;
}

export const defaultValue: Readonly<IClassUnit> = {};
