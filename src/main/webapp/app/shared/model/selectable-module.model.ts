import { ICourseUnit } from 'app/shared/model/course-unit.model';

export interface ISelectableModule {
  id?: number;
  name?: string;
  courseUnits?: ICourseUnit[];
}

export const defaultValue: Readonly<ISelectableModule> = {};
