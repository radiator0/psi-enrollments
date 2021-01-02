import { IClassSchedule } from 'app/shared/model/class-schedule.model';
import { IClassUnit } from 'app/shared/model/class-unit.model';

export interface IRoom {
  id?: number;
  number?: string;
  classSchedules?: IClassSchedule[];
  classUnits?: IClassUnit[];
  buildingId?: number;
}

export const defaultValue: Readonly<IRoom> = {};
