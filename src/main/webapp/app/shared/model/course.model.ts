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
  classGroups?: IClassGroup[];
  specialties?: ISpecialty[];
  enrollmentDateId?: number;
  courseUnitId?: number;
}

export const defaultValue: Readonly<ICourse> = {};
