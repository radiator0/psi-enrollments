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
import CourseRow from './course-row';
import TogetherCourseBlockDetails from '../../../shared/model/domain/dto/together-course-block-details';
import CourseDetails from '../../../shared/model/domain/dto/course-details';

interface ITogetherCourseBlockRowProps {
    togetherCourseBlock: TogetherCourseBlockDetails,
    onSelected: (course: CourseDetails) => void
};

interface ITogetherCourseBlockRowState {
    isOpen: boolean
};

class TogetherCourseBlockRow extends Component<ITogetherCourseBlockRowProps, ITogetherCourseBlockRowState> {
    constructor(props: ITogetherCourseBlockRowProps) {
        super(props);

        this.state = {
            isOpen: false
        };
    }

    setOpen(isOpen: boolean) {
        this.setState({ isOpen })
    }

    isEnrolledInAll() {
        return this.props.togetherCourseBlock.courses.every(c => c.enrolled);
    }

    renderExpandableItem() {
        const { togetherCourseBlock } = this.props;
        const { isOpen } = this.state;

        return (
            <ListItem>
                <ListItemAvatar>
                    <Avatar>
                        {this.isEnrolledInAll() ? <CheckIcon /> : <ClearIcon />}
                    </Avatar>
                </ListItemAvatar>
                <ListItemText
                    primary={togetherCourseBlock.name}
                    secondary={'Blok scalony'}
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
        const { togetherCourseBlock, onSelected } = this.props;
        return (
            <>
                {togetherCourseBlock.courses.map(x => 
                    <CourseRow key={x.id} course={x} onSelected={onSelected} />
                )}
            </>
        );
    }

    shouldExpand() {
        return this.props.togetherCourseBlock.courses.length > 1;
    }

    render() {
        const { togetherCourseBlock } = this.props;
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

export default TogetherCourseBlockRow;