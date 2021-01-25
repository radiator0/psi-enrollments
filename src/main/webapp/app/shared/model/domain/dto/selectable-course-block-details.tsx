import CourseUnitDetails from './course-unit-details';

export default class SelectableCourseBlockDetails {
  public readonly id: number;
  public readonly name: string;
  public readonly courseUnits: Array<CourseUnitDetails>;

  constructor(id: number, name: string, courseUnits: Array<CourseUnitDetails>) {
    this.id = id;
    this.name = name;
    this.courseUnits = courseUnits;
  }
};