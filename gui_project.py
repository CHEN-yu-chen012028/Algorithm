from datetime import datetime
import tkinter as tk
from tkinter import ttk, messagebox
import time
import threading

# ==========================================
# 1. 漏桶演算法核心 (警衛)
# ==========================================
class LeakyBucket:
    def __init__(self, capacity: float, leak_rate: float):
        self.capacity = capacity
        self.leak_rate = leak_rate
        self.water_level = 0.0          
        self.last_check_time = time.time() 

    def allow_request(self) -> tuple[bool, float]:
        current_time = time.time()
        time_passed = current_time - self.last_check_time
        self.last_check_time = current_time

        # 計算漏水
        leaked_water = time_passed * self.leak_rate
        self.water_level = max(0.0, self.water_level - leaked_water)

        # 檢查是否滿了
        if self.water_level + 1.0 <= self.capacity:
            self.water_level += 1.0  
            return True, self.water_level
        else:
            return False, self.water_level

# 初始化漏桶：容量 5，每秒漏掉 1 格水
bucket = LeakyBucket(capacity=5.0, leak_rate=1.0)

# ==========================================
# 2. 視窗介面與互動 (GUI)
# ==========================================
root = tk.Tk()
root.title("漏桶演算法 - 防暴力破解專題視覺化儀表板")
root.geometry("750x400")
root.resizable(False, False)

# 更新水桶視覺化進度條與標籤
def update_bucket_ui(current_water):
    water_progress['value'] = (current_water / bucket.capacity) * 100
    lbl_water_status['text'] = f"當前水量: {current_water:.2f} / {bucket.capacity}"

# 定時器：讓水桶在沒人點擊時，畫面的水也會自動慢慢降下去
def bg_leak_monitor():
    while True:
        current_time = time.time()
        time_passed = current_time - bucket.last_check_time
        leaked = time_passed * bucket.leak_rate
        bucket.water_level = max(0.0, bucket.water_level - leaked)
        bucket.last_check_time = current_time
        
        # 更新介面
        try:
            update_bucket_ui(bucket.water_level)
        except:
            break # 視窗關閉時退出
        time.sleep(0.1)

# 模擬處理登入要求
def handle_login(source="手動登入"):
    allowed, current_water = bucket.allow_request()
    update_bucket_ui(current_water)

    # 新增資訊（固定模擬 IP）
    ip = "127.0.0.1"
    now = datetime.now().strftime("%Y-%m-%d %H:%M:%S")

    if not allowed:
        status = 429
        log_box.insert(
            tk.END,
            f"[IP:{ip}] [時間:{now}] [HTTP {status}] ❌ [{source}] 偵測到惡意密集請求！漏桶溢出，直接攔截\n"
        )
        log_box.see(tk.END)
        return False

    # 水桶沒滿，檢查密碼
    u = entry_user.get()
    p = entry_pwd.get()

    if u == "admin" and p == "1234":
        status = 200
        log_box.insert(
            tk.END,
            f"[IP:{ip}] [時間:{now}] [HTTP {status}] ✅ [{source}] 密碼正確，登入成功！\n"
        )
    else:
        status = 401
        log_box.insert(
            tk.END,
            f"[IP:{ip}] [時間:{now}] [HTTP {status}] ⚠️ [{source}] 驗證失敗：密碼錯誤\n"
        )

    log_box.see(tk.END)
    return True

# 壞人：自動化暴力破解攻擊腳本
def run_attack():
    btn_attack['state'] = 'disabled'
    log_box.insert(tk.END, "🔥 啟動駭客自動化暴力破解工具，每 0.1 秒嘗試一次...\n")
    
    def attack_thread():
        for i in range(15):
            # 在 GUI 執行緒中安全地呼叫登入邏輯
            root.after(0, lambda: handle_login(source="駭客攻擊"))
            time.sleep(0.1) # 超高速敲門
        root.after(0, lambda: btn_attack.config(state='normal'))
        
    threading.Thread(target=attack_thread).start()

# --- 介面排版 ---
# 左側：好人登入區
frame_left = tk.LabelFrame(root, text=" 網站登入入口 (模擬使用者) ", padx=10, pady=10)
frame_left.place(x=20, y=20, width=250, height=180)

tk.Label(frame_left, text="帳號:").grid(row=0, column=0, pady=5, sticky="w")
entry_user = tk.Entry(frame_left, width=18)
entry_user.insert(0, "admin")
entry_user.grid(row=0, column=1, pady=5)

tk.Label(frame_left, text="密碼:").grid(row=1, column=0, pady=5, sticky="w")
entry_pwd = tk.Entry(frame_left, show="*", width=18)
entry_pwd.insert(0, "wrong_password")
entry_pwd.grid(row=1, column=1, pady=5)

btn_login = tk.Button(frame_left, text="安全登入", bg="#4CAF50", fg="white", width=20, command=lambda: handle_login("手動登入")) # ✅ width 移動到這裡
btn_login.grid(row=2, column=0, columnspan=2, pady=10) 
frame_right = tk.LabelFrame(root, text=" 駭客攻擊模擬器 ", padx=10, pady=10)
frame_right.place(x=20, y=210, width=250, height=160)

tk.Label(frame_right, text="模擬自動化字典攻擊工具\n(極快速度連續發送請求)").pack(pady=5)
btn_attack = tk.Button(frame_right, text="💥 發動暴力破解攻擊", bg="#F44336", fg="white", font=("Arial", 10, "bold"), command=run_attack)
btn_attack.pack(pady=10, fill="x")

# 中間：漏桶水庫即時狀態 (演算法骨架視覺化)
frame_middle = tk.LabelFrame(root, text=" 漏桶演算法即時狀態監控 (核心防禦) ", padx=10, pady=10)
frame_middle.place(x=290, y=20, width=440, height=110)

lbl_water_status = tk.Label(frame_middle, text="當前水量: 0.00 / 5.0", font=("Arial", 10, "bold"))
lbl_water_status.pack(anchor="w")

water_progress = ttk.Progressbar(frame_middle, orient="horizontal", length=400, mode="determinate")
water_progress.pack(pady=10)

# 下方：系統日誌 Log 輸出
frame_bottom = tk.LabelFrame(root, text=" 系統後台防禦日誌 (Log) ", padx=10, pady=10)
frame_bottom.place(x=290, y=140, width=440, height=230)

log_box = tk.Text(frame_bottom, width=55, height=10, font=("Consolas", 9))
log_box.pack(side="left", fill="both", expand=True)
scrollbar = tk.Scrollbar(frame_bottom, command=log_box.yview)
scrollbar.pack(side="right", fill="y")
log_box.config(yscrollcommand=scrollbar.set)

# 啟動背景水桶自然漏水監聽執行緒
threading.Thread(target=bg_leak_monitor, daemon=True).start()

root.mainloop()