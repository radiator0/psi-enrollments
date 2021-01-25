import SelectableCourseBlockDetails from "../../shared/model/domain/dto/selectable-course-block-details";

export default class CoursesData {
    public readonly selectableCourseBlocks: SelectableCourseBlockDetails;

    constructor(selectableCourseBlocks : SelectableCourseBlockDetails) {
        this.selectableCourseBlocks = selectableCourseBlocks;
    }
}