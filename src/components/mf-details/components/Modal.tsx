import '../stylesheets/Modal.css'
const Modal = ({setIsModalOpen} : any) =>{
    
    const onClickHandler = () => {
        window.location.reload();
    }

    return (
    <div className="modal-container" onClick={onClickHandler}>
        <div className='modal-msg-container'>
            <h3 className='center'>Message Details</h3>
            <h4 className='center' id="styles-msg">Successfully</h4>
            <div id="modal-btn-wrapper">
                <button id="modal-btn-ok" onClick={onClickHandler}>OK</button>
            </div>
            
        </div>
    </div>)
}

export default Modal;