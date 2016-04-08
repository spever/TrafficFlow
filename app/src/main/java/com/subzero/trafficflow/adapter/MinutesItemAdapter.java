package com.subzero.trafficflow.adapter;

import android.content.Context;
import android.service.carrier.CarrierService;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.subzero.trafficflow.R;
import com.subzero.trafficflow.bean.Minutes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by hui on 2016/3/10.
 */
public class MinutesItemAdapter extends BaseAdapter {
    private ArrayList<Minutes> listRank;
    private Context context;
    private int type;


    public MinutesItemAdapter(ArrayList<Minutes> listRank, Context context, int type) {
        this.listRank = listRank;
        this.context = context;
        this.type = type;

    }

    public MinutesItemAdapter(Context context) {
        this.listRank = new ArrayList<Minutes>();
        this.context = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (listRank != null)
            return listRank.size();
        return 0;
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return listRank.get(arg0);
    }

    public void addItem(List<Minutes> list) {
        this.listRank.addAll(list);
        notifyDataSetChanged();
    }

    public void refreshItem(List<Minutes> list) {
        this.listRank.clear();
        this.listRank.addAll(list);
        notifyDataSetChanged();
    }

    public List<Minutes> getList() {
        return listRank;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        // TODO Auto-generated method stub

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.xlistitem_item_minutes, null);
            holder = new ViewHolder();
            holder.id_tv_rank_item = (TextView) convertView.findViewById(R.id.id_tv_rank_item);
            holder.id_tv_name = (TextView) convertView.findViewById(R.id.id_tv_name);
            holder.id_tv_pollutecount = (TextView) convertView.findViewById(R.id.id_tv_pollutecount);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Minutes nb = listRank.get(position);
        holder.id_tv_rank_item.setText(String.valueOf(position + 1));
        int count = nb.getSJXH();
        String time = "";
        switch (count) {
            case 1:
                time = "00:00";
                break;
            case 2:
                time = "00:05";
                break;
            case 3:
                time = "00:10";
                break;
            case 4:
                time = "00:15";
                break;
            case 5:
                time = "00:20";
                break;
            case 6:
                time = "00:25";
                break;
            case 7:
                time = "00:30";
                break;
            case 8:
                time = "00:35";
                break;
            case 9:
                time = "00:40";
                break;
            case 10:
                time = "00:45";
                break;
            case 11:
                time = "00:50";
                break;
            case 12:
                time = "00:55";
                break;
            case 13:
                time = "01:00";
                break;
            case 14:
                time = "01:05";
                break;
            case 15:
                time = "01:10";
                break;
            case 16:
                time = "01:15";
                break;
            case 17:
                time = "01:20";
                break;
            case 18:
                time = "01:25";
                break;
            case 19:
                time = "01:30";
                break;
            case 20:
                time = "01:35";
                break;
            case 21:
                time = "01:40";
                break;
            case 22:
                time = "01:45";
                break;
            case 23:
                time = "01:50";
                break;
            case 24:
                time = "01:55";
                break;
            case 25:
                time = "02:00";
                break;
            case 26:
                time = "02:05";
                break;
            case 27:
                time = "02:10";
                break;
            case 28:
                time = "02:15";
                break;
            case 29:
                time = "02:20";
                break;
            case 30:
                time = "02:25";
                break;
            case 31:
                time = "02:30";
                break;
            case 32:
                time = "02:35";
                break;
            case 33:
                time = "02:40";
                break;
            case 34:
                time = "02:45";
                break;
            case 35:
                time = "02:50";
                break;
            case 36:
                time = "02:55";
                break;
            case 37:
                time = "03:00";
                break;
            case 38:
                time = "03:05";
                break;
            case 39:
                time = "03:10";
                break;
            case 40:
                time = "03:15";
                break;
            case 41:
                time = "03:20";
                break;
            case 42:
                time = "03:25";
                break;
            case 43:
                time = "03:30";
                break;
            case 44:
                time = "03:35";
                break;
            case 45:
                time = "03:40";
                break;
            case 46:
                time = "03:45";
                break;
            case 47:
                time = "03:50";
                break;
            case 48:
                time = "03:55";
                break;
            case 49:
                time = "04:00";
                break;
            case 50:
                time = "04:05";
                break;
            case 51:
                time = "04:10";
                break;
            case 52:
                time = "04:15";
                break;
            case 53:
                time = "04:20";
                break;
            case 54:
                time = "04:25";
                break;
            case 55:
                time = "04:30";
                break;
            case 56:
                time = "04:35";
                break;
            case 57:
                time = "04:40";
                break;
            case 58:
                time = "04:45";
                break;
            case 59:
                time = "04:50";
                break;
            case 60:
                time = "04:55";
                break;
            case 61:
                time = "05:00";
                break;
            case 62:
                time = "05:05";
                break;
            case 63:
                time = "05:10";
                break;
            case 64:
                time = "05:15";
                break;
            case 65:
                time = "05:20";
                break;
            case 66:
                time = "05:25";
                break;
            case 67:
                time = "05:30";
                break;
            case 68:
                time = "05:35";
                break;
            case 69:
                time = "05:40";
                break;
            case 70:
                time = "05:45";
                break;
            case 71:
                time = "05:50";
                break;
            case 72:
                time = "05:55";
                break;
            case 73:
                time = "06:00";
                break;
            case 74:
                time = "06:05";
                break;
            case 75:
                time = "06:10";
                break;
            case 76:
                time = "06:15";
                break;
            case 77:
                time = "06:20";
                break;
            case 78:
                time = "06:25";
                break;
            case 79:
                time = "06:30";
                break;
            case 80:
                time = "06:35";
                break;
            case 81:
                time = "06:40";
                break;
            case 82:
                time = "06:45";
                break;
            case 83:
                time = "06:50";
                break;
            case 84:
                time = "06:55";
                break;
            case 85:
                time = "07:00";
                break;
            case 86:
                time = "07:05";
                break;
            case 87:
                time = "07:10";
                break;
            case 88:
                time = "07:15";
                break;
            case 89:
                time = "07:20";
                break;
            case 90:
                time = "07:25";
                break;
            case 91:
                time = "07:30";
                break;
            case 92:
                time = "07:35";
                break;
            case 93:
                time = "07:40";
                break;
            case 94:
                time = "07:45";
                break;
            case 95:
                time = "07:50";
                break;
            case 96:
                time = "07:55";
                break;
            case 97:
                time = "08:00";
                break;
            case 98:
                time = "08:05";
                break;
            case 99:
                time = "08:10";
                break;
            case 100:
                time = "08:15";
                break;
            case 101:
                time = "08:20";
                break;
            case 102:
                time = "08:25";
                break;
            case 103:
                time = "08:30";
                break;
            case 104:
                time = "08:35";
                break;
            case 105:
                time = "08:40";
                break;
            case 106:
                time = "08:45";
                break;
            case 107:
                time = "08:50";
                break;
            case 108:
                time = "08:55";
                break;
            case 109:
                time = "09:00";
                break;
            case 110:
                time = "09:05";
                break;
            case 111:
                time = "09:10";
                break;
            case 112:
                time = "09:15";
                break;
            case 113:
                time = "09:20";
                break;
            case 114:
                time = "09:25";
                break;
            case 115:
                time = "09:30";
                break;
            case 116:
                time = "09:35";
                break;
            case 117:
                time = "09:40";
                break;
            case 118:
                time = "09:45";
                break;
            case 119:
                time = "09:50";
                break;
            case 120:
                time = "09:55";
                break;
            case 121:
                time = "10:00";
                break;
            case 122:
                time = "10:05";
                break;
            case 123:
                time = "10:10";
                break;
            case 124:
                time = "10:15";
                break;
            case 125:
                time = "10:20";
                break;
            case 126:
                time = "10:25";
                break;
            case 127:
                time = "10:30";
                break;
            case 128:
                time = "10:35";
                break;
            case 129:
                time = "10:40";
                break;
            case 130:
                time = "10:45";
                break;
            case 131:
                time = "10:50";
                break;
            case 132:
                time = "10:55";
                break;
            case 133:
                time = "11:00";
                break;
            case 134:
                time = "11:05";
                break;
            case 135:
                time = "11:10";
                break;
            case 136:
                time = "11:15";
                break;
            case 137:
                time = "11:20";
                break;
            case 138:
                time = "11:25";
                break;
            case 139:
                time = "11:30";
                break;
            case 140:
                time = "11:35";
                break;
            case 141:
                time = "11:40";
                break;
            case 142:
                time = "11:45";
                break;
            case 143:
                time = "11:50";
                break;
            case 144:
                time = "11:55";
                break;
            case 145:
                time = "12:00";
                break;
            case 146:
                time = "12:05";
                break;
            case 147:
                time = "12:10";
                break;
            case 148:
                time = "12:15";
                break;
            case 149:
                time = "12:20";
                break;
            case 150:
                time = "12:25";
                break;
            case 151:
                time = "12:30";
                break;
            case 152:
                time = "12:35";
                break;
            case 153:
                time = "12:40";
                break;
            case 154:
                time = "12:45";
                break;
            case 155:
                time = "12:50";
                break;
            case 156:
                time = "12:55";
                break;
            case 157:
                time = "13:00";
                break;
            case 158:
                time = "13:05";
                break;
            case 159:
                time = "13:10";
                break;
            case 160:
                time = "13:15";
                break;
            case 161:
                time = "13:20";
                break;
            case 162:
                time = "13:25";
                break;
            case 163:
                time = "13:30";
                break;
            case 164:
                time = "13:35";
                break;
            case 165:
                time = "13:40";
                break;
            case 166:
                time = "13:45";
                break;
            case 167:
                time = "13:50";
                break;
            case 168:
                time = "13:55";
                break;
            case 169:
                time = "14:00";
                break;
            case 170:
                time = "14:05";
                break;
            case 171:
                time = "14:10";
                break;
            case 172:
                time = "14:15";
                break;
            case 173:
                time = "14:20";
                break;
            case 174:
                time = "14:25";
                break;
            case 175:
                time = "14:30";
                break;
            case 176:
                time = "14:35";
                break;
            case 177:
                time = "14:40";
                break;
            case 178:
                time = "14:45";
                break;
            case 179:
                time = "14:50";
                break;
            case 180:
                time = "14:55";
                break;
            case 181:
                time = "15:00";
                break;
            case 182:
                time = "15:05";
                break;
            case 183:
                time = "15:10";
                break;
            case 184:
                time = "15:15";
                break;
            case 185:
                time = "15:20";
                break;
            case 186:
                time = "15:25";
                break;
            case 187:
                time = "15:30";
                break;
            case 188:
                time = "15:35";
                break;
            case 189:
                time = "15:40";
                break;
            case 190:
                time = "15:45";
                break;
            case 191:
                time = "15:50";
                break;
            case 192:
                time = "15:55";
                break;
            case 193:
                time = "16:00";
                break;
            case 194:
                time = "16:05";
                break;
            case 195:
                time = "16:10";
                break;
            case 196:
                time = "16:15";
                break;
            case 197:
                time = "16:20";
                break;
            case 198:
                time = "16:25";
                break;
            case 199:
                time = "16:30";
                break;
            case 200:
                time = "16:35";
                break;
            case 201:
                time = "16:40";
                break;
            case 202:
                time = "16:45";
                break;
            case 203:
                time = "16:50";
                break;
            case 204:
                time = "16:55";
                break;
            case 205:
                time = "17:00";
                break;
            case 206:
                time = "17:05";
                break;
            case 207:
                time = "17:10";
                break;
            case 208:
                time = "17:15";
                break;
            case 209:
                time = "17:20";
                break;
            case 210:
                time = "17:25";
                break;
            case 211:
                time = "17:30";
                break;
            case 212:
                time = "17:35";
                break;
            case 213:
                time = "17:40";
                break;
            case 214:
                time = "17:45";
                break;
            case 215:
                time = "17:50";
                break;
            case 216:
                time = "17:55";
                break;
            case 217:
                time = "18:00";
                break;
            case 218:
                time = "18:05";
                break;
            case 219:
                time = "18:10";
                break;
            case 220:
                time = "18:15";
                break;
            case 221:
                time = "18:20";
                break;
            case 222:
                time = "18:25";
                break;
            case 223:
                time = "18:30";
                break;
            case 224:
                time = "18:35";
                break;
            case 225:
                time = "18:40";
                break;
            case 226:
                time = "18:45";
                break;
            case 227:
                time = "18:50";
                break;
            case 228:
                time = "18:55";
                break;
            case 229:
                time = "19:00";
                break;
            case 230:
                time = "19:05";
                break;
            case 231:
                time = "19:10";
                break;
            case 232:
                time = "19:15";
                break;
            case 233:
                time = "19:20";
                break;
            case 234:
                time = "19:25";
                break;
            case 235:
                time = "19:30";
                break;
            case 236:
                time = "19:35";
                break;
            case 237:
                time = "19:40";
                break;
            case 238:
                time = "19:45";
                break;
            case 239:
                time = "19:50";
                break;
            case 240:
                time = "19:55";
                break;
            case 241:
                time = "20:00";
                break;
            case 242:
                time = "20:05";
                break;
            case 243:
                time = "20:10";
                break;
            case 244:
                time = "20:15";
                break;
            case 245:
                time = "20:20";
                break;
            case 246:
                time = "20:25";
                break;
            case 247:
                time = "20:30";
                break;
            case 248:
                time = "20:35";
                break;
            case 249:
                time = "20:40";
                break;
            case 250:
                time = "20:45";
                break;
            case 251:
                time = "20:50";
                break;
            case 252:
                time = "20:55";
                break;
            case 253:
                time = "21:00";
                break;
            case 254:
                time = "21:05";
                break;
            case 255:
                time = "21:10";
                break;
            case 256:
                time = "21:15";
                break;
            case 257:
                time = "21:20";
                break;
            case 258:
                time = "21:25";
                break;
            case 259:
                time = "21:30";
                break;
            case 260:
                time = "21:35";
                break;
            case 261:
                time = "21:40";
                break;
            case 262:
                time = "21:45";
                break;
            case 263:
                time = "21:50";
                break;
            case 264:
                time = "21:55";
                break;
            case 265:
                time = "22:00";
                break;
            case 266:
                time = "22:05";
                break;
            case 267:
                time = "22:10";
                break;
            case 268:
                time = "22:15";
                break;
            case 269:
                time = "22:20";
                break;
            case 270:
                time = "22:25";
                break;
            case 271:
                time = "22:30";
                break;
            case 272:
                time = "22:35";
                break;
            case 273:
                time = "22:40";
                break;
            case 274:
                time = "22:45";
                break;
            case 275:
                time = "22:50";
                break;
            case 276:
                time = "22:55";
                break;
            case 277:
                time = "23:00";
                break;
            case 278:
                time = "23:05";
                break;
            case 279:
                time = "23:10";
                break;
            case 280:
                time = "23:15";
                break;
            case 281:
                time = "23:20";
                break;
            case 282:
                time = "23:25";
                break;
            case 283:
                time = "23:30";
                break;
            case 284:
                time = "23:35";
                break;
            case 285:
                time = "23:40";
                break;
            case 286:
                time = "23:45";
                break;
            case 287:
                time = "23:50";
                break;
            case 288:
                time = "23:55";
                break;

        }
        holder.id_tv_name.setText(time);
        switch (type) {
            case 1:
                holder.id_tv_pollutecount.setText(String.valueOf(nb.getJDC_CS()));
                break;
            case 2:
                holder.id_tv_pollutecount.setText(String.valueOf(nb.getKC_DL()));
                break;
            case 3:
                holder.id_tv_pollutecount.setText(String.valueOf(nb.getHC_DL()));
                break;
            case 4:
                holder.id_tv_pollutecount.setText(String.valueOf(nb.getJDC_CS()));
                break;

        }

        return convertView;
    }

    public static class ViewHolder {
        public TextView id_tv_rank_item;
        public TextView id_tv_name;
        public TextView id_tv_pollutecount;

    }
}
