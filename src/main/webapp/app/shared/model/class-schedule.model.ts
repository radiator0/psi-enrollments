import { Moment } from 'moment';
import { DayOfWeek } from 'app/shared/model/enumerations/day-of-week.model';
import { WeekType } from 'app/shared/model/enumerations/week-type.model';
import { SemesterPeriod } from 'app/shared/model/enumerations/semester-period.model';

export interface IClassSchedule {
  id?: number;
  dayOfWeek?: DayOfWeek;
  weekType?: WeekType;
  semesterPeriod?: SemesterPeriod;
  startTime?: string;
  endTime?: string;
  classGroupId?: number;
  roomId?: number;
}

export const defaultValue: Readonly<IClassSchedule> = {};
