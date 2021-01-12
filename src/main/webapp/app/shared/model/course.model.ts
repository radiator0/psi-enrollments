import { IEnrollmentDate } from 'app/shared/model/enrollment-date.model';
import { IClassGroup } from 'app/shared/model/class-group.model';
import { ISpecialty } from 'app/shared/model/specialty.model';
import { ClassType } from 'app/shared/model/enumerations/class-type.model';

export interface ICourse {
  id?: number;
  name?: string;
  shortName?: string;
  code?: string;
  ects?: number;
  form?: ClassType;
  enrollmentDates?: IEnrollmentDate[];
  classGroups?: IClassGroup[];
  specialties?: ISpecialty[];
  courseUnitId?: number;
}

export const defaultValue: Readonly<ICourse> = {};
