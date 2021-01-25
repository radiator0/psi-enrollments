import SelectableCourseBlockDetails from '../../../../shared/model/domain/dto/selectable-course-block-details';
import CoursesData from '../../courses-data';

export default function mapSelectableCourseBlockToCoursesData(scbd : SelectableCourseBlockDetails) {
    return new CoursesData(scbd);
}