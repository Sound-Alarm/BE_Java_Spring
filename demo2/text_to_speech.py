import tkinter as tk
from tkinter import ttk, scrolledtext
from gtts import gTTS
import os
import pygame
import tempfile

class TextToSpeechApp:
    def __init__(self, root):
        self.root = root
        self.root.title("Chuyển đổi văn bản thành giọng nói")
        self.root.geometry("600x400")
        
        # Khởi tạo pygame mixer
        pygame.mixer.init()
        
        # Tạo frame chính
        main_frame = ttk.Frame(root, padding="10")
        main_frame.grid(row=0, column=0, sticky=(tk.W, tk.E, tk.N, tk.S))
        
        # Label hướng dẫn
        ttk.Label(main_frame, text="Nhập văn bản cần chuyển đổi:").grid(row=0, column=0, sticky=tk.W, pady=5)
        
        # Text area để nhập văn bản
        self.text_area = scrolledtext.ScrolledText(main_frame, width=60, height=10)
        self.text_area.grid(row=1, column=0, pady=5)
        
        # Combobox để chọn ngôn ngữ
        ttk.Label(main_frame, text="Chọn ngôn ngữ:").grid(row=2, column=0, sticky=tk.W, pady=5)
        self.language_var = tk.StringVar()
        self.language_combo = ttk.Combobox(main_frame, textvariable=self.language_var)
        self.language_combo['values'] = ('vi', 'en', 'fr', 'de', 'ja', 'ko')
        self.language_combo.current(0)
        self.language_combo.grid(row=3, column=0, sticky=tk.W, pady=5)
        
        # Nút chuyển đổi
        self.convert_button = ttk.Button(main_frame, text="Chuyển đổi", command=self.convert_text)
        self.convert_button.grid(row=4, column=0, pady=20)
        
        # Label trạng thái
        self.status_label = ttk.Label(main_frame, text="")
        self.status_label.grid(row=5, column=0, pady=5)

    def convert_text(self):
        text = self.text_area.get("1.0", tk.END).strip()
        if not text:
            self.status_label.config(text="Vui lòng nhập văn bản!")
            return
            
        try:
            self.status_label.config(text="Đang chuyển đổi...")
            self.root.update()
            
            # Tạo file tạm thời
            temp_file = tempfile.NamedTemporaryFile(delete=False, suffix='.mp3')
            temp_filename = temp_file.name
            temp_file.close()
            
            # Chuyển đổi văn bản thành giọng nói
            tts = gTTS(text=text, lang=self.language_var.get())
            tts.save(temp_filename)
            
            # Phát âm thanh bằng pygame
            pygame.mixer.music.load(temp_filename)
            pygame.mixer.music.play()
            
            # Đợi cho đến khi phát xong
            while pygame.mixer.music.get_busy():
                self.root.update()
                pygame.time.Clock().tick(10)
            
            # Unload file âm thanh (chỉ có ở pygame >=2.0.0)
            try:
                pygame.mixer.music.unload()
            except AttributeError:
                pass  # Nếu phiên bản pygame không hỗ trợ unload
            
            # Xóa file tạm
            os.unlink(temp_filename)
            
            self.status_label.config(text="Chuyển đổi thành công!")
        except Exception as e:
            self.status_label.config(text=f"Lỗi: {str(e)}")

if __name__ == "__main__":
    root = tk.Tk()
    app = TextToSpeechApp(root)
    root.mainloop() 