import { ISemester } from 'app/shared/model/semester.model';

export interface IFieldOfStudy {
  id?: number;
  name?: string;
  uniqueName?: string;
  semesters?: ISemester[];
  studyProgramId?: number;
}

export const defaultValue: Readonly<IFieldOfStudy> = {};
