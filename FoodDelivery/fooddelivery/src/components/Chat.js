import React, {
    Component
} from "react";
import {
    Launcher
} from "react-chat-window";
import axios from "axios";
import NavBar from "./NavBar"
import { Button } from 'react-bootstrap'

class ChatBox extends Component {
    constructor(props) {
        super(props);
        var rep = false
        if (localStorage.type === "2") {
            rep = true
        }
        localStorage.setItem('rep', false)
        this.state = {
            messageList: [],
            rep: rep
        };
        this.escalate = this.escalate.bind(this);
    }

    componentDidMount() {
        this.apiSubCall();
    }

    escalate() {
        localStorage.setItem("rep", true)
    }

    async apiSubCall() {
        console.log("inside sub");
        if (localStorage.userData) {
            let userData = JSON.parse(localStorage.userData);
            if (userData) {
                let subId = localStorage.name;
                console.log("subId", subId);
                while (true) {
                    await axios
                        .get(
                            `https://us-central1-jeyanth-project.cloudfunctions.net/function-1?id=${subId}`
                        )
                        .then((res) => {
                            let data = res.data;
                            if (res.status === 200 && data && data.length) {
                                console.log(localStorage)
                                Object.values(data).forEach((value) => {
                                    let author = value.message.senderId;
                                    console.log(value.message)
                                    console.log("val", value.message.userType)
                                    console.log("local", localStorage.type)
                                    if ((author !== userData.email_id) && (value.message.userType !== localStorage.type) && localStorage.rep === "false") {
                                        let text = value.message.userName + ": " + value.message.text;
                                        this.setState({
                                            messageList: [
                                                ...this.state.messageList,
                                                {
                                                    author: "them",
                                                    type: "text",
                                                    data: {
                                                        text,
                                                    },
                                                },
                                            ],
                                        });
                                    }
                                });
                            }
                        })
                        .catch((err) => {
                            console.log("error", err)
                            console.log("subscriber received with error.");
                        });
                    await new Promise((r) => setTimeout(r, 5000));
                }
            }
        }
    }

    _onMessageWasSent(message) {
        this.setState({
            messageList: [...this.state.messageList, message],
        });
        this.apiPubCall(message);
    }

    _onFilesSelected() {
        let messageObj = {
            senderId: "me",
            data: {
                text: "Current chat does not support file sharing. It is under construction",
            },
            type: "text",
        };

        let messages = this.state.messageList;
        messages.push(messageObj);
        this.setState({
            messageList: messages,
        });
    }

    async apiPubCall(message) {
        if (localStorage.userData) {
            var chatName = ""
            let userData = JSON.parse(localStorage.userData);
            if (
                userData && localStorage.name && localStorage.type
            ) {
                if (localStorage.type === "2") {
                    chatName = "Representative"
                } else if (localStorage.type === "3") {
                    chatName = "Manager"
                } else {
                    chatName = localStorage.name
                }
                let name = userData.email_id;
                let messageObj = {
                    senderId: name,
                    text: message.data.text,
                    type: message.type,
                    userName: chatName,
                    userType: localStorage.type,
                };

                let obj = {
                    message: messageObj,
                };

                await axios
                    .post(
                        "https://us-central1-jeyanth-project.cloudfunctions.net/publish",
                        obj
                    )
                    .then((res) => console.log("publisher res data", res.data))
                    .catch((err) => console.log("publisher err data", err));
            }
        }
    }

    _sendMessage(text) {
        if (text.length > 0) {
            this.setState({
                messageList: [
                    ...this.state.messageList,
                    {
                        author: "me",
                        type: "text",
                        data: {
                            text,
                        },
                    },
                ],
            });
        }
    }

    render() {
        return (<div>
            <NavBar></NavBar>
            {this.state.rep && <Button className="w-100" onClick={this.escalate}>Escalate</Button>}

            <Launcher agentProfile={
                {
                    teamName: "Chat",
                    imageUrl: "https://img.icons8.com/plasticine/100/000000/chat.png",
                }
            }
                onMessageWasSent={
                    this._onMessageWasSent.bind(this)
                }
                messageList={
                    this.state.messageList
                }
                showEmoji={
                    false
                }
                _showFilePicker={
                    false
                }
                onFilesSelected={
                    this._onFilesSelected.bind(this)
                }
            />
        </div>
        );
    }
}

export default ChatBox;