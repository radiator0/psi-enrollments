import { IClassGroup } from 'app/shared/model/class-group.model';
import { IClassSchedule } from 'app/shared/model/class-schedule.model';

export interface ILecturer {
  id?: number;
  firstName?: string;
  secondName?: string;
  lastName?: string;
  mail?: string;
  title?: string;
  classGroups?: IClassGroup[];
  classSchedules?: IClassSchedule[];
}

export const defaultValue: Readonly<ILecturer> = {};
