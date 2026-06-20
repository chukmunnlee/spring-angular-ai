import {NgClass} from '@angular/common';
import { Component, ElementRef, signal, ViewChild } from '@angular/core';
import {FormsModule} from '@angular/forms';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {MatToolbarModule} from '@angular/material/toolbar';

export interface ChatMessage {
  text: string
  isBot: boolean
}

@Component({
  selector: 'app-simple-chat',
  imports: [ FormsModule, NgClass, MatCardModule, MatToolbarModule, MatInputModule
      , MatFormFieldModule, MatButtonModule, MatIconModule
  ],
  templateUrl: './simple-chat.html',
  styleUrl: './simple-chat.scss',
})
export class SimpleChat {

  @ViewChild('#chatHistory')
  private chatHistory!: ElementRef;

  protected isLoading = false
  protected userPrompt = signal<string>('')

  protected readonly messages = signal<ChatMessage[]>([
    { text: 'Hello, how can I help you today?', isBot: true }
  ])

  protected sendMessage(): void {
    const msg = this.userPrompt().trim()
    if (!!msg) {
      this.updateChatHistory(msg)
      this.isLoading = true;
      this.simulateResponse()
    }
    this.userPrompt.set('')
  }

  private updateChatHistory(msg: string, isBot = false) {
      this.messages.update(messages => [ ...messages, { text: msg, isBot } ])
      this.scrollToBottom()
  }

  private simulateResponse(): void {
    setTimeout(() => {
      const resp = 'This is a simulated response'
      this.updateChatHistory(resp, true)
      this.isLoading = false
    }, 2000)
  }

  private scrollToBottom() {
    try {
      this.chatHistory.nativeElement.scrollTop = this.chatHistory.nativeElement.scrollHeight
    } catch (err) {
    }
  }
}
