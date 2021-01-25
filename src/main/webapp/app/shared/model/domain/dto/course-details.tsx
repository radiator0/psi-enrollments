import { ClassType } from "../../enumerations/class-type.model";

export default class CourseDetails {
  public readonly id: number;
  public readonly code: string;
  public readonly name: string;
  public readonly shortName: string;
  public readonly ects: number;
  public readonly form: ClassType;
  public readonly specialities: Array<string>;
  public readonly enrolled: boolean;

  constructor(id: number, code: string, name: string, shortName: string, ects: number, form: ClassType, specialities: Array<string>, enrolled: boolean) {
    this.id = id;
    this.code = code;
    this.name = name;
    this.shortName = shortName;
    this.ects = ects;
    this.form = form;
    this.specialities = specialities;
    this.enrolled = enrolled;
  }
};