import React, { Component } from 'react';
import ListItem from '@material-ui/core/ListItem';
import ListItemAvatar from '@material-ui/core/ListItemAvatar';
import ListItemSecondaryAction from '@material-ui/core/ListItemSecondaryAction';
import ListItemText from '@material-ui/core/ListItemText';
import IconButton from '@material-ui/core/IconButton';
import KeyboardArrowDownIcon from '@material-ui/icons/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@material-ui/icons/KeyboardArrowUp';
import Collapse from '@material-ui/core/Collapse';
import CourseRow from './course-row';
import CourseUnitDetails from '../../../../shared/model/domain/dto/course-unit-details';
import CourseDetails from '../../../../shared/model/domain/dto/course-details';
import { translate } from 'react-jhipster';
import { CheckedAvatar, UnCheckedAvatar } from '../../custom-avatars';
import log from 'app/config/log';

interface ICourseUnitRowRowProps {
    courseUnit: CourseUnitDetails,
    onSelected: (course: CourseDetails) => void,
    selectedCourse: CourseDetails
};

interface ICourseUnitRowRowState {
    isOpen: boolean
};

class CourseUnitRow extends Component<ICourseUnitRowRowProps, ICourseUnitRowRowState> {
    constructor(props: ICourseUnitRowRowProps) {
        super(props);

        this.state = {
            isOpen: false
        };
    }

    setOpen(isOpen: boolean) {
        this.setState({ isOpen })
    }

    isEnrolledInAll() {
        return this.props.courseUnit.courses.every(c => c.studentEnrolled);
    }

    renderExpandableItem() {
        const { courseUnit } = this.props;
        const { isOpen } = this.state;

        return (
            <ListItem>
                <ListItemAvatar>
                    {this.isEnrolledInAll() ? <CheckedAvatar /> : <UnCheckedAvatar />}
                </ListItemAvatar>
                <ListItemText
                    primary={courseUnit.code}
                    secondary={<span style={{ display: 'flex' }}>
                    <span style={{ width: '100%' }}>
                        {courseUnit.isGroupOfCourses ?  translate('enrolling.course.courseGroup') : courseUnit.isStream ? translate('enrolling.course.stream') : ''}
                    </span>
                    <span style={{ width: '100%', marginRight: '0px', textAlign: 'right' }}>
                        {courseUnit.ects ? `${courseUnit.ects} ECTS` : ''}
                    </span>
                </span>}
                />
                <ListItemSecondaryAction>
                    <IconButton aria-label="expand row" size="small" onClick={() => this.setOpen(!isOpen)}>
                        {isOpen ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
                    </IconButton>
                </ListItemSecondaryAction>
            </ListItem>
        );
    }

    renderCourseRows() {
        const { courseUnit, onSelected, selectedCourse } = this.props;
        return (
            <>
                {courseUnit.courses.map(x =>
                    <CourseRow key={x.id} course={x} onSelected={onSelected} selectedCourse={selectedCourse}  />
                )}
            </>
        );
    }

    shouldExpand() {
        return this.props.courseUnit.courses.length > 1;
    }

    render() {
        const { courseUnit } = this.props;
        const { isOpen } = this.state;

        return (
            <React.Fragment>
                {this.shouldExpand() ? (
                    <>
                        {this.renderExpandableItem()}
                        <Collapse in={isOpen} timeout="auto" unmountOnExit style={{ paddingLeft: 20 }}>
                            {this.renderCourseRows()}
                        </Collapse>
                    </>
                ) : (
                        this.renderCourseRows()
                    )}
            </React.Fragment>
        );
    }
}

export default CourseUnitRow;