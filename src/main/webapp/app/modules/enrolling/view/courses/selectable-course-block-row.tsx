import React, { Component } from 'react';
import ListItem from '@material-ui/core/ListItem';
import ListItemAvatar from '@material-ui/core/ListItemAvatar';
import ListItemSecondaryAction from '@material-ui/core/ListItemSecondaryAction';
import ListItemText from '@material-ui/core/ListItemText';
import Avatar from '@material-ui/core/Avatar';
import IconButton from '@material-ui/core/IconButton';
import CheckIcon from '@material-ui/icons/Check';
import ClearIcon from '@material-ui/icons/Clear';
import KeyboardArrowDownIcon from '@material-ui/icons/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@material-ui/icons/KeyboardArrowUp';
import Collapse from '@material-ui/core/Collapse';
import CourseUnitRow from './course-unit-row';
import SelectableCourseBlockDetails from '../../../../shared/model/domain/dto/selectable-course-block-details';
import CourseDetails from '../../../../shared/model/domain/dto/course-details';
import { translate } from 'react-jhipster';
import { CheckedAvatar, UnCheckedAvatar } from '../../custom-avatars';
import { Typography } from '@material-ui/core';

interface ISelectableCourseBlockRowProps {
    selectableCourseBlock: SelectableCourseBlockDetails;
    onSelected: (course: CourseDetails) => void;
    selectedCourse: CourseDetails;
}

interface ISelectableCourseBlockRowState {
    isOpen: boolean;
}

class SelectableCourseBlockRow extends Component<ISelectableCourseBlockRowProps, ISelectableCourseBlockRowState> {
    constructor(props: ISelectableCourseBlockRowProps) {
        super(props);

        this.state = {
            isOpen: false,
        };
    }

    setOpen(isOpen: boolean) {
        this.setState({ isOpen });
    }

    isEnrolledInAll() {
        return this.props.selectableCourseBlock.courseUnits.some(tb => tb.courses.every(c => c.studentEnrolled));
    }

    rendedExpandableItem() {
        const { selectableCourseBlock } = this.props;
        const { isOpen } = this.state;
        const isStreamBlock = selectableCourseBlock.courseUnits.every(cu => cu.isStream);
        return (
            <ListItem>
                <ListItemAvatar>
                {this.isEnrolledInAll() ? <CheckedAvatar /> : <UnCheckedAvatar />}
                </ListItemAvatar>
                <ListItemText
                    primary={selectableCourseBlock.name}
                    secondary={
                        <Typography variant='body2' style={{ width: '100%' }}>
                            {isStreamBlock ? translate('enrolling.course.streamModule') : translate('enrolling.course.selectableModule')}
                        </Typography>
                    }
                />
                <ListItemSecondaryAction>
                    <IconButton aria-label="expand row" size="small" onClick={() => this.setOpen(!isOpen)}>
                        {isOpen ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
                    </IconButton>
                </ListItemSecondaryAction>
            </ListItem>
        );
    }

    renderEmbeddedRows() {
        const { selectableCourseBlock, onSelected, selectedCourse } = this.props;
        return (
            <>
                {selectableCourseBlock.courseUnits.map((x, id) =>
                    <CourseUnitRow key={id} courseUnit={x} onSelected={onSelected} selectedCourse={selectedCourse} />
                )}
            </>
        );
    }

    shouldExpand() {
        return this.props.selectableCourseBlock.courseUnits.length > 1;
    }

    render() {
        const { isOpen } = this.state;

        return (
            <React.Fragment>
                {this.shouldExpand() ? (
                    <>
                        {this.rendedExpandableItem()}
                        <Collapse in={isOpen} timeout="auto" unmountOnExit style={{ paddingLeft: 20 }}>
                            {this.renderEmbeddedRows()}
                        </Collapse>
                    </>
                ) : (
                        this.renderEmbeddedRows()
                    )}
            </React.Fragment>
        );
    }
}

export default SelectableCourseBlockRow;
