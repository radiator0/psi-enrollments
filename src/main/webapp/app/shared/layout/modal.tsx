import React from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import { Translate } from 'react-jhipster';

interface IModalOption {
    optionNameKey: string,
    onClick: () => void
}

interface IAlertDialogProps {
    title: string,
    content: string,
    options: Array<IModalOption>
}

const renderOptions = (options: Array<IModalOption>) => {
    return (
        <>
            {options.map(o =>
                <Button key={o.optionNameKey} onClick={o.onClick} color="primary">
                    <Translate contentKey={o.optionNameKey}>{o.optionNameKey}</Translate>
                </Button>)}
        </>
    );
}

export default function AlertDialog(props: IAlertDialogProps) {
    return (
        <div>
            <Dialog
                open={true}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >
                <DialogTitle id="alert-dialog-title">{props.title}</DialogTitle>
                <DialogContent>
                    <DialogContentText id="alert-dialog-description">
                        {props.content}
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    {renderOptions(props.options)}
                </DialogActions>
            </Dialog>
        </div>
    );
}
